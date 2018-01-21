/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdvertiserBoTestModule } from '../../../test.module';
import { MediaUserComponent } from '../../../../../../main/webapp/app/entities/media-user/media-user.component';
import { MediaUserService } from '../../../../../../main/webapp/app/entities/media-user/media-user.service';
import { MediaUser } from '../../../../../../main/webapp/app/entities/media-user/media-user.model';

describe('Component Tests', () => {

    describe('MediaUser Management Component', () => {
        let comp: MediaUserComponent;
        let fixture: ComponentFixture<MediaUserComponent>;
        let service: MediaUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [MediaUserComponent],
                providers: [
                    MediaUserService
                ]
            })
            .overrideTemplate(MediaUserComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MediaUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MediaUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MediaUser(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mediaUsers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
