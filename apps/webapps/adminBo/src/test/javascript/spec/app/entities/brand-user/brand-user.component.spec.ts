/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdminBoTestModule } from '../../../test.module';
import { BrandUserComponent } from '../../../../../../main/webapp/app/entities/brand-user/brand-user.component';
import { BrandUserService } from '../../../../../../main/webapp/app/entities/brand-user/brand-user.service';
import { BrandUser } from '../../../../../../main/webapp/app/entities/brand-user/brand-user.model';

describe('Component Tests', () => {

    describe('BrandUser Management Component', () => {
        let comp: BrandUserComponent;
        let fixture: ComponentFixture<BrandUserComponent>;
        let service: BrandUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [BrandUserComponent],
                providers: [
                    BrandUserService
                ]
            })
            .overrideTemplate(BrandUserComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new BrandUser(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.brandUsers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
