/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdminBoTestModule } from '../../../test.module';
import { MediaComponent } from '../../../../../../main/webapp/app/entities/media/media.component';
import { MediaService } from '../../../../../../main/webapp/app/entities/media/media.service';
import { Media } from '../../../../../../main/webapp/app/entities/media/media.model';

describe('Component Tests', () => {

    describe('Media Management Component', () => {
        let comp: MediaComponent;
        let fixture: ComponentFixture<MediaComponent>;
        let service: MediaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [MediaComponent],
                providers: [
                    MediaService
                ]
            })
            .overrideTemplate(MediaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MediaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MediaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Media(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.media[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
